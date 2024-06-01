const prisma = require("../prisma/prisma");
const jwt = require("jsonwebtoken");


const accessValidation = (req, res, next) => {
    const {authorization} = req.headers;

    if (!authorization){
        return res.status(401).json({
            "status": false,
            "message": "Unauthorized access",
        })
    }


    const token = authorization.split(" ")[1];
    const secret = process.env.SECRET_KEY;

    try{
        const decoded = jwt.verify(token, secret);
        req.parent = decoded;

        prisma.parent.findUnique({
            where: {
                parent_id: decoded.payload
            }
        }).then(parent => {
            if (!parent){
                return res.status(401).json({
                    "status": false,
                    "message": "Unauthorized access",
                })
            }
            next()
        }).catch(error => {
            return res.status(401).json({
                "status": false,
                "message": "Unauthorized access",
            })
        })

    }catch(error){
        return res.status(401).json({
            "status": false,
            "message": "Unauthorized access",
        })
    }
}

module.exports = accessValidation;