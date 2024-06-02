# Backend API Documentation 🧑‍💻

## StuntGuard Backend API

## API Endpoints

| Endpoint                          | Method | Input                                                                         | Description                       | Status         |
|-----------------------------------|--------|-------------------------------------------------------------------------------|-----------------------------------|----------------|
| /register                         | POST   | fullname, gender, email, phone, password, confirmpassword                     | Register account                  |✅ Completed   |
| /login                            | POST   | email, password                                                               | Login to application              |✅ Completed   |
| /getUsers                         | GET    | -                                                                             | Get all parents data              |✅ Completed   |
| /getUserbyId/:parent_id           | GET    | parent_id                                                                     | Get parent data by ID             |✅ Completed   |
| /updateUser/:parent_id            | POST   | fullname, gender, email, phone (optional)                                     | Update parent data                |✅ Completed   |
| /updatePassword/:parent_id        | POST   | oldPassword, newPassword, confirmPassword                                     | Update parent data                |✅ Completed   |
| /removeUser/:parent_id            | DELETE | -                                                                             | Remove parent account             |✅ Completed   |
| /addChild/:child_id               | POST   | child_name, child_gender, born_date, born_weight, born_height, breastfeeding  | Add child data to family          |✅ Completed   |
| /getChilds                        | GET    | -                                                                             | Get all children data             |✅ Completed   |
| /getChildbyId/:child_id           | GET    | need child_id                                                                 | Get child data by ID              |✅ Completed   |
| /getParentChilds/:parent_id       | GET    | need parent_id                                                                | Get childrens data by parent ID   |✅ Completed   |
| /updateChild/:child_id            | POST   | child_name, child_gender, born_date, born_weight, born_height, breastfeeding  | Update child data                 |✅ Completed   |
| /removeChild/:parent_id           | DELETE | -                                                                             | Remove child data                 |✅ Completed   |
| /prediction/:child_id             | POST   | child_weigth, child_height                                                    | Post a prediction                 |🟠 Ongoing     |
| /getAllPredicts/                  | GET    | -                                                                             | Get all predictions               |🟠 Ongoing     |
| /getPredictbyId/:predict_id       | GET    | need predict_id                                                               | Get predictions by predict_id     |🟠 Ongoing     |
| /getAllPredictbyChild/:child_id   | GET    | need child_id                                                                 | Get predictions by child_id       |🟠 Ongoing     |


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
