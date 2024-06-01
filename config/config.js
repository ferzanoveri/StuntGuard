const dotenv = require("dotenv");
dotenv.config();

const { DB_HOST, DB_USER, DB_NAME, DB_PASSWORD, DB_CONNECTION, PORT, SECRET_ACCESS_TOKEN } = process.env;

module.exports = { DB_HOST, DB_USER, DB_NAME, DB_PASSWORD, DB_CONNECTION, PORT, SECRET_ACCESS_TOKEN };