package com.backend.DonateDrift.Controller;

import com.backend.DonateDrift.dtos.FundraiserRequest;

import com.backend.DonateDrift.entity.Attachment;
import com.backend.DonateDrift.entity.CoverAttachment;
import com.backend.DonateDrift.entity.Fundraiser;
import com.backend.DonateDrift.entity.User;
import com.backend.DonateDrift.enums.Category;
import com.backend.DonateDrift.exception.UserException;
import com.backend.DonateDrift.repository.AttachmentRepository;
import com.backend.DonateDrift.repository.CoverAttachmentRepository;
import com.backend.DonateDrift.repository.FundraiserRepository;
import com.backend.DonateDrift.repository.UserRepository;
import com.backend.DonateDrift.service.CloudinaryImageService;
import com.backend.DonateDrift.service.FundraiserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@RestController
@RequestMapping("/api/fundraisers")
public class FundraiserController {

    private final FundraiserService fundraiserService;

    @Autowired
    public FundraiserController(FundraiserService fundraiserService) {
        this.fundraiserService = fundraiserService;
    }

    @Autowired
	private CloudinaryImageService cloudinaryImageService;

    @Autowired
    public AttachmentRepository attachmentRepository;

    @Autowired
    public FundraiserRepository fundraiserRepository;

    @Autowired
    public UserRepository userRepository;

    @Autowired
    public CoverAttachmentRepository coverAttachmentRepository;

//    @GetMapping
//    public ResponseEntity<Page<Fundraiser>> getAllFundraisers(@RequestParam Integer pageNumber,
//			@RequestParam Integer pageSize) {
//        Page<Fundraiser> fundraisers = fundraiserService.getAllFundraisers(pageNumber,pageSize);
//        return new ResponseEntity<>(fundraisers,HttpStatus.OK);
//    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Fundraiser> getFundraiserById(@PathVariable long id) {
    	Fundraiser fundraiser = fundraiserRepository.getAllById(id);
    	return new ResponseEntity<>(fundraiser,HttpStatus.OK);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<Page<Fundraiser>> getFundraiserById(@PathVariable long id,@RequestParam Integer pageNumber,
			@RequestParam Integer pageSize) {
        Page<Fundraiser> fundraiser = fundraiserService.getFundraiserById(id,pageNumber,pageSize);
        return new ResponseEntity<>(fundraiser,HttpStatus.OK);
    }

    @PostMapping(value="/{id}", consumes = {"multipart/mixed", "multipart/form-data"})
    public ResponseEntity<Fundraiser> createFundraiser(@ModelAttribute FundraiserRequest fundraiserRequest,@PathVariable Long id) throws UserException, GeneralSecurityException, IOException {
        Fundraiser fundraiser = new Fundraiser();
        String tt = fundraiserRequest.getTitle();
        StringBuilder str = new StringBuilder();
        
        for(int i=0;i<tt.length();i++) {
        	if(tt.charAt(0)=='"') {
            	continue;
            }
        	str.append(tt.charAt(i));
        }

        fundraiser.setTitle(str.toString());
        fundraiser.setCategory(fundraiserRequest.getCategory());
        fundraiser.setCountry(fundraiserRequest.getCountry());
        fundraiser.setCity(fundraiserRequest.getCity());
        fundraiser.setFirstName(fundraiserRequest.getFirstName());
        fundraiser.setLastName(fundraiserRequest.getLastName());
        fundraiser.setDescription(fundraiserRequest.getDescription());
        fundraiser.setUpiId(fundraiserRequest.getUpiId()); //***********
        fundraiser.setRaisedAmount(0L);
        fundraiser.setRequiredAmount(fundraiserRequest.getRequiredAmount());
        fundraiser.setVideoUrl(fundraiserRequest.getVideoUrl());
        fundraiser.setCreatedAt(LocalDateTime.now());

        String data = this.cloudinaryImageService.upload(fundraiserRequest.getCoverPhoto());
        CoverAttachment coverAttachment = new CoverAttachment();
        coverAttachment.setUrl1(data);

        fundraiser.setCoverAttachment(coverAttachment);
        
    
        List<MultipartFile> file = new ArrayList<>();
        if(fundraiserRequest.getFile1()!=null) {
        	file.add(fundraiserRequest.getFile1());
        }
        if(fundraiserRequest.getFile2()!=null) {
        	file.add(fundraiserRequest.getFile2());
        }
        if(fundraiserRequest.getFile3()!=null) {
        	file.add(fundraiserRequest.getFile3());
        }
        if(fundraiserRequest.getFile4()!=null) {
        	file.add(fundraiserRequest.getFile4());
        }
        if(fundraiserRequest.getFile5()!=null) {
        	file.add(fundraiserRequest.getFile5());
        }
        
        List<Attachment> attachment = new ArrayList<>();
        for(MultipartFile i:file){
            Attachment attach = new Attachment();
            attach.setUrl(this.cloudinaryImageService.upload(i));
            attachment.add(attach);
        }
        fundraiser.setAttachment(attachment);
        Long userId = id;
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.getFundraiser().add(fundraiser);
            userRepository.save(user);
            fundraiser.setUser(user);
        } else {
            throw new UserException("User Not Found!!");
        }
        fundraiserRepository.save(fundraiser);
        for(Attachment i:attachment){
            i.setFundraiser(fundraiser);
            attachmentRepository.save(i);
        }
        coverAttachment.setFundraiser(fundraiser);
        coverAttachmentRepository.save(coverAttachment);
        return new ResponseEntity<>(fundraiser, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Fundraiser> updateFundraiser(@PathVariable long id, @RequestBody FundraiserRequest fundraiserRequest) {
        Fundraiser updatedFundraiser = fundraiserService.updateFundraiser(id, fundraiserRequest);
        return new ResponseEntity<>(updatedFundraiser, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFundraiser(@PathVariable long id) {
        fundraiserService.deleteFundraiser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/withdraw/{fundraiserId}")
    public ResponseEntity<String> checkAndWithdrawFunds(@PathVariable Long fundraiserId) {
        try {
            Fundraiser fundraiser = fundraiserService.findFundraiserById(fundraiserId);
            double raisedAmount = fundraiser.getRaisedAmount();
            double requiredAmount = fundraiser.getRequiredAmount();

            // Check if raised amount is less than 50% of the required amount
            if (raisedAmount < requiredAmount * 0.5) {
                return ResponseEntity.badRequest().body("Not enough balance at the moment.");
            }

            // If raised amount is 50% or more but less than 100%
            if (raisedAmount >= requiredAmount * 0.5 && raisedAmount < requiredAmount) {
                double amountToWithdraw = requiredAmount / 2; // Calculate 50% of the raised amount
                fundraiserService.withdrawFunds(fundraiser, amountToWithdraw);
                return ResponseEntity.ok("Half of the funds have been successfully withdrawn.");
            }

            // If raised amount is 100% or more
            if (raisedAmount >= requiredAmount) {
                fundraiserService.withdrawFunds(fundraiser, raisedAmount);  // Withdraw all the raised amount
                return ResponseEntity.ok("All funds have been successfully withdrawn.");
            }

            return ResponseEntity.badRequest().body("Unexpected error during withdrawal.");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error processing the withdrawal: " + e.getMessage());
        }
    }



}
