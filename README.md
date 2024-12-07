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
