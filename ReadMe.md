# Backend API Documentation 🧑‍💻

## StuntGuard Backend API

## API Endpoints

| Endpoint                   | Method | Input                                                                         | Description                       |
|----------------------------|--------|-------------------------------------------------------------------------------|-----------------------------------|
| /register                  | POST   | fullname, gender, email, phone, password, confirmpassword                     | Register account                  |
| /login                     | POST   | email, password                                                               | Login to application              |
| /getUsers                  | GET    | -                                                                             | Get all parents data              |
| /getUserbyId/:parent_id    | GET    | parent_id                                                                     | Get parent data by ID             |
| /updateUser/:parent_id     | POST   | fullname, gender, email, phone, password, confirmpassword                     | Update parent data                |
| /removeUser/:parent_id     | DELETE | -                                                                             | Remove parent account             |
| /addChild/:child_id        | POST   | child_name, child_gender, born_date, born_weight, born_height, breastfeeding  | Add child data to family          |
| /getChilds                 | GET    | -                                                                             | Get all children data             |
| /getChildbyId/:child_id    | GET    | need child_id                                                                 | Get child data by ID              |
| /getParentChilds/:parent_id| GET    | need parent_id                                                                | Get childrens data by parent ID   |
| /updateChild/:child_id     | POST   | child_name, child_gender, born_date, born_weight, born_height, breastfeeding  | Update child data                 |
| /removeChild/:parent_id    | DELETE | -                                                                             | Remove child data                 |

## How to run this API on your local machine 💻

If you want to run this API Server on your local machine, you need to do this steps:

1. Clone this repository. `git clone -b backend https://github.com/ferzanoveri/StuntGuard.git`
2. Change directory to the root project
3. Type `npm ci` in the terminal to install all dependencies needed.
4. Activate your xampp and create database name `stuntguard-db`.
5. Type `npx prisma migrate dev` in the terminal to migrate your databases (if needed, type `npx prisma generate` first).
6. Run your application with `npm run start-dev` in the terminal.
