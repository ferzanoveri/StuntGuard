//Middlewares
const express = require("express");
const router = express.Router();
const auth = require("../middleware/auth");
const validation = require("../middleware/validation");
const childController = require("../controller/childController");
const userController = require("../controller/userController");
const newsController = require("../controller/newsController");
const predictController = require("../controller/predictController");
const recomController = require("../controller/recomController");

router.get("/", (req, res) => {
    res.status(200).json({
        "message": "Backend API StuntGuard Successful"
    })
});

// Parents
router.post('/register', auth.register)
router.post('/login', auth.login)
router.get('/user/all', validation, userController.getUsers)
router.get('/user/id/:parent_id', validation, userController.getUserbyId)
router.put('/user/update/:parent_id', validation, userController.updateUser)
router.put('/user/updatePassword/:parent_id', validation, userController.updatePassword)
router.delete('/user/remove/:parent_id', validation, userController.removeUser)

// Child
router.post('/child/:parent_id', validation, childController.addChild);
router.put('/child/update/:child_id', validation, childController.updateChild);
router.get('/parent/childs/:parent_id', validation, childController.getParentChilds);
router.get('/child/all', validation, childController.getChilds);
router.get('/child/id/:child_id', validation, childController.getChildbyId);
router.delete('/child/remove/:child_id', validation, childController.removeChild);

// News
router.get('/news', (req, res) => {
    res.redirect('/news/relevansi');
});
router.get('/news/:result_type?', validation, newsController.getNews);
router.get('/news/:result_type?/:token', validation, newsController.getNewsDetails);

// Predict
router.post('/predict/:child_id', validation, predictController.postPredict);
router.get('/predict/all', validation, predictController.getAllPredictions);
router.get('/predict/id/:predict_id', validation, predictController.getPredictionById);
router.get('/predict/child/:child_id', validation, predictController.getPredictionsByChild);
router.post('/notes/:predict_id', validation, predictController.postNotes);
router.get('/get_notes/:predict_id', validation, predictController.getNotes);

// Recommendations
router.post('/recom/:predict_id', validation, recomController.postRecom);
router.get('/recom/all', validation, recomController.getAllRecommendations);
router.get('/recom/child/:child_id', validation, recomController.getRecommendationsByChild);
router.get('/recom/id/:recommendation_id/foods', validation, recomController.getFoodDetailsByRecommendation);


module.exports = router;