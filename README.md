# **Donate Drift**

Donate Drift is a crowdfunding platform designed to make fundraising simple, secure, and trustworthy. Built using **Spring Boot**, **Java**, **SQL**, and a **ReactJS** frontend, this project allows users to create fundraisers, collect donations via PayPal, and track their progress. The platform also ensures authenticity through an admin verification process.

---

## **Features**

### **User Features**
- **Create Fundraisers**: Users can start a fundraiser by filling out a detailed form about their cause.
- **Payment Integration**: Donations are processed securely through the **PayPal API**.
- **Real-Time Updates**: Fundraisers can post updates about their progress or share images (e.g., medical reports) with donors.
- **Transparency**: Track the amount raised and the remaining amount required for each fundraiser.

### **Admin Features**
- **Verification Process**: Admins review submitted documents or videos to validate fundraisers before they are visible to the public.
- **Admin Dashboard**: Manage all fundraisers, ensuring quality and authenticity.

### **Security**
- **JWT Authentication**: Ensures secure user authentication and interaction.
- **Secure Payments**: Leveraging PayPal's trusted payment system.

---

## **Tech Stack**
- **Backend**: Spring Boot, Java
- **Frontend**: ReactJS
- **Database**: SQL
- **Payment Gateway**: PayPal API
- **Authentication**: JWT Tokens

---

## **Requirements**
To run this project, ensure you have the following installed:

1. **Java** (version 8 or higher)
2. **Maven** (for managing dependencies and building the project)
3. **MySQL** (or any SQL-compatible database)
4. **Node.js** and **npm** (for the ReactJS frontend)
5. **PayPal Developer Account** (to obtain API credentials)

---

## **Installation Instructions**

### 1. **Backend Setup**
1. Clone the repository:
   ```bash
   git clone https://github.com/your-username/donate-drift.git
   cd donate-drift/backend
2. Update database configurations in application.properties:
   ```bash
   spring.datasource.url=jdbc:mysql://localhost:3306/donate_drift
   spring.datasource.username=your_username
   spring.datasource.password=your_password
3. Build and run the backend:
   ```bash
   mvn clean install
   mvn spring-boot:run

### 2. **Frontend Setup**
1. Navigate to the frontend directory:
   ```bash
   cd ../frontend
2. Install dependencies:
   ```bash
   npm install
3. Start the ReactJS development server:
   ```bash
   npm start
   
### 3. **Database Setup**
1. Create a MySQL database:
   ```sql
   CREATE DATABASE donate_drift;
2. Run the backend to allow Spring Boot to auto-create the required tables:
   ```bash
   mvn spring-boot:run
   
### 4. **PayPal Integration**
1. Create a developer account at [PayPal Developer](https://developer.paypal.com/).
2. Generate **Client ID** and **Secret** from the PayPal Developer Dashboard.
3. Add the credentials to `application.properties` in the backend project:
   ```properties
   paypal.client.id=your_client_id
   paypal.client.secret=your_client_secret
   paypal.mode=sandbox
Replace your_client_id and your_client_secret with the actual credentials from PayPal.
The paypal.mode property can be set to sandbox for testing or live for production.

## **How to Use**
1. **Launch the backend and frontend servers**:
   - Ensure that the backend server is running:
     ```bash
     cd backend
     mvn spring-boot:run
     ```
   - Start the frontend development server:
     ```bash
     cd ../frontend
     npm start
     ```

2. **Open the frontend in your browser**:
   - By default, the application will be available at [http://localhost:3000](http://localhost:3000).

### **For Users**:
- Sign up and log in to create fundraisers.
- Monitor the progress of your fundraisers through the dashboard and view real-time updates.

### **For Admins**:
- Log in to the admin dashboard to manage and verify fundraisers to ensure authenticity and quality.

## **Future Enhancements**
- **Add support for additional payment gateways**: Integrate more payment options to provide users with flexibility in choosing how to donate.
- **Implement advanced analytics for fundraisers**: Add features to analyze fundraising trends, donor behavior, and other insights to improve campaign strategies.
- **Introduce AI-powered fraud detection**: Utilize machine learning algorithms to detect and prevent fraudulent activities on the platform.
- **Expand real-time communication features**: Enhance the real-time feed to include live chat or notifications for better user engagement and interaction.



