//Middlewares
const express = require("express");
const router = express.Router();
const auth = require("../middleware/auth");
const validation = require("../middleware/validation");
const childController = require("../controller/childController");
const userController = require("../controller/userController");
const predictController = require("../controller/predictController");

router.get("/", (req, res) => {
    res.status(200).json({
        "message": "API StuntGuard Successful"
    })
});

// Parents
router.post('/register', auth.register)
router.post('/login', auth.login)
router.get('/getUsers', userController.getUsers)
router.get('/getUserbyId/:parent_id', userController.getUserbyId)
router.post('/updateUser/:parent_id', userController.updateUser)
router.post('/updatePassword/:parent_id', userController.updatePassword)
router.delete('/removeUser/:parent_id', userController.removeUser)

// Child
router.post('/addChild/:parent_id', childController.addChild);
router.delete('/removeChild/:child_id', childController.removeChild);
router.post('/updateChild/:child_id', childController.updateChild);
router.get('/getParentChilds/:parent_id', childController.getParentChilds);
router.get('/getChilds', childController.getChilds);
router.get('/getChildbyId/:child_id', childController.getChildbyId);

// Predict
router.post('/prediction/:child_id', predictController.createPrediction)
router.get('/getAllPredicts', predictController.getAllPredicts)
router.get('/getPredictbyId/:predict_id', predictController.getPredictbyId)
router.get('/getAllPredictbyChild/:child_id', predictController.getAllPredictbyChild)

module.exports = router;