//Middlewares
const express = require("express");
const router = express.Router();
const auth = require("../middleware/auth");
const validation = require("../middleware/validation");
const childController = require("../controller/childController");
const userController = require("../controller/userController");

router.get("/", (req, res) => {
    res.status(200).json({
        "message": "API StuntGuard Successful"
    })
});

// Parents
router.post('/register', auth.register)
router.post('/login', auth.login)
router.get('/user/all', userController.getUsers)
router.get('/user/id/:parent_id', userController.getUserbyId)
router.post('/user/update/:parent_id', userController.updateUser)
router.post('/user/updatePassword/:parent_id', userController.updatePassword)
router.delete('/user/remove/:parent_id', userController.removeUser)

// Child
router.post('/child/:parent_id', childController.addChild);
router.post('/updateChild/:child_id', childController.updateChild);
router.get('/parent/childs/:parent_id', childController.getParentChilds);
router.get('/child/all', childController.getChilds);
router.get('/child/id/:child_id', childController.getChildbyId);
router.delete('/child/remove/:child_id', childController.removeChild);

module.exports = router;