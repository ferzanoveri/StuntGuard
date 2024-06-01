# Backend API Documentation 🧑‍💻

## StuntGuard Backend API

| Endpoint | Method |                   Input                   |                Description                | JWT Token |
| :---------: | :------: | :------------------------------------------: | :------------------------------------------: | :---------: |
| /register |  POST  | fullname, gender, email, phone, password, confirmpassword |       Register account      |  &#9744;  |
|  /login  |  POST  |              email, password              | Login to application |  &#9744;  |
|  /getUsers  |  GET  |                     -                     |             Get all parents data             |  &#9745;  |

## How to run this API on your local machine 💻

If you want to run this API Server on your local machine, you need to do this steps:

- clone this repository. `git clone -b backend https://github.com/ferzanoveri/StuntGuard.git`
- change directory to the root project
- type `npm ci` in the terminal to install all dependencies needed.
- activate your xampp and create database name `stuntguard-db`.
- type `npx prisma migrate dev` in the terminal to migrate your databases (if needed, type `npx prisma generate` first).
- run your application with `npm run start-dev` in the terminal.
