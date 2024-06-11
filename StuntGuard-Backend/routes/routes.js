//Middlewares
const express = require("express");
const router = express.Router();
const auth = require("../middleware/auth");
const validation = require("../middleware/validation");
const childController = require("../controller/childController");
const userController = require("../controller/userController");
const newsController = require("../controller/newsController");

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
router.put('/user/update/:parent_id', userController.updateUser)
router.put('/user/updatePassword/:parent_id', userController.updatePassword)
router.delete('/user/remove/:parent_id', userController.removeUser)

// Child
router.post('/child/:parent_id', childController.addChild);
router.put('/child/update/:child_id', childController.updateChild);
router.get('/parent/childs/:parent_id', childController.getParentChilds);
router.get('/child/all', childController.getChilds);
router.get('/child/id/:child_id', childController.getChildbyId);
router.delete('/child/remove/:child_id', childController.removeChild);

//News
router.get('/news', (req, res) => {
    res.redirect('/news/1');
});
router.get('/news/:page/:result_type?', newsController.getNews);
router.get('/news/:page/next', newsController.getNextPage);
router.get('/news/:page/back', newsController.getPreviousPage);
router.get('/news/:page/:token', newsController.getNewsDetails);

module.exports = router;