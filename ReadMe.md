# Backend API Documentation 🧑‍💻

## StuntGuard Backend API

## API Endpoints

| Endpoint                          | Method | Input                                                                         | Description                       | Status         |
|-----------------------------------|--------|-------------------------------------------------------------------------------|-----------------------------------|----------------|
| /register                         | POST   | fullname, gender, email, phone, password, confirmpassword                     | Register account                  |
| /login                            | POST   | email, password                                                               | Login to application              |
| /getUsers                         | GET    | -                                                                             | Get all parents data              |
| /getUserbyId/:parent_id           | GET    | parent_id                                                                     | Get parent data by ID             |
| /updateUser/:parent_id            | POST   | fullname, gender, email, phone (optional)                                     | Update parent data                |
| /updatePassword/:parent_id        | POST   | oldPassword, newPassword, confirmPassword                                     | Update parent data                |
| /removeUser/:parent_id            | DELETE | -                                                                             | Remove parent account             |
| /addChild/:child_id               | POST   | child_name, child_gender, born_date, born_weight, born_height, breastfeeding  | Add child data to family          |
| /getChilds                        | GET    | -                                                                             | Get all children data             |
| /getChildbyId/:child_id           | GET    | need child_id                                                                 | Get child data by ID              |
| /getParentChilds/:parent_id       | GET    | need parent_id                                                                | Get childrens data by parent ID   |
| /updateChild/:child_id            | POST   | child_name, child_gender, born_date, born_weight, born_height, breastfeeding  | Update child data                 |
| /removeChild/:parent_id           | DELETE | -                                                                             | Remove child data                 |
| /prediction/:child_id             | POST   | child_weigth, child_height                                                    | Post a prediction                 |
| /getAllPredicts/                  | GET    | -                                                                             | Get all predictions               |
| /getPredictbyId/:predict_id       | GET    | need predict_id                                                               | Get predictions by predict_id     |
| /getAllPredictbyChild/:child_id   | GET    | need child_id                                                                 | Get predictions by child_id       |


## How to run this API on your local machine 💻

If you want to run this API Server on your local machine, you need to do this steps:

1. Clone this repository. `git clone -b backend https://github.com/ferzanoveri/StuntGuard.git`
2. Change directory to the root project
3. Type `npm ci` in the terminal to install all dependencies needed.
4. Activate your xampp and create database name `stuntguard-db`.
5. Type `npx prisma migrate dev` in the terminal to migrate your databases (if needed, type `npx prisma generate` first).
6. Run your application with `npm run start-dev` in the terminal.

# Cloud Services

## ![Cloud Architecture](images/CloudArchitecture.png)

## Deskripsi Layanan Cloud

1. **Cloud Run**
   - **Deskripsi:** Cloud Run digunakan sebagai backend service, selain itu juga untuk menyebarkan model machine learning dan endpoint API menggunakan layanan terkontainer.
   - **Fungsi:** Menyediakan lingkungan yang dapat diskalakan untuk aplikasi berbasis kontainer.

3. **Cloud Storage**
   - **Deskripsi:** Cloud Storage digunakan untuk menyimpan aset dan model machine learning.
   - **Fungsi:** Menyediakan penyimpanan yang tahan lama dan aman untuk data dalam jumlah besar.

4. **Cloud SQL**
   - **Deskripsi:** Cloud SQL digunakan untuk menyimpan data relasional berupa data pengguna, data anak, serta data hasil prediksi model ml.
   - **Fungsi:** Mengelola basis data relasional dengan MySQL.

6. **Postman**
   - **Deskripsi:** Postman digunakan untuk menguji API backend.
   - **Fungsi:** Menyediakan alat untuk mengembangkan, menguji, dan mendokumentasikan API.

## ![Database Diagram](images/DatabaseDiagram.png)
