//Middlewares
const express = require("express");
const router = express.Router();
const auth = require("../middleware/auth");
const accessValidation = require("../middleware/validation");

router.get("/", (req, res) => {
    res.status(200).json({
        "message": "API StuntGuard Successful"
    })
});

// Auth routes
router.post('/register', auth.register)
router.post('/login', auth.login)
router.get('/getUsers', auth.getUsers)

module.exports = router;