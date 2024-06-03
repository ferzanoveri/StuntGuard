const prisma = require("../prisma/prisma")
const bcrypt = require("bcrypt")
const jwt = require("jsonwebtoken")

const createToken = (payload) => {
    return jwt.sign({ payload }, process.env.SECRET_KEY, {
        expiresIn: 30 * 24 * 60 * 60
    })
}

exports.register = async (req, res) => {

    const { parent_name, email, phone, password, confirmPassword } = req.body

    if (password !== confirmPassword) {
        return res.status(400).json({
            "status": false,
            "message": "Passwords do not match",
        })
    }

    //check if email already exists
    const exists = await prisma.parent.count({
        where: {
            email: email
        }
     });
    if (exists > 0) {
        return res.status(400).json({
            "status": false,
            "message": "Email already exists",
        })
    }

    //encrypt the password
    const salt = await bcrypt.genSalt();
    const hashedPassword = await bcrypt.hash(password, salt)

    try {
        const newParents = await prisma.parent.create({
            data: {
                parent_name: parent_name,
                email: email,
                phone: phone,
                password: hashedPassword,
            },
        },
    )
    const token = createToken(newParents.parent_id)
    res.status(200).json({
        "status": true,
        "message": "Account created successfully",
        "data": newParents,
        "token": token
    })
    } catch (error) {
        console.error(error)
        res.status(500).json({
            "status": false,
            "message": "An unexpected error occurred on the server",
        })
    }
}

exports.login = async (req, res) => {
    const { email, password } = req.body
    const parent = await prisma.parent.findUnique({
        where: {
            email: email
        }
    })

    if (!parent){
        return res.status(400).json({
            "status": false,
            "message": "Invalid email",
        })
    }

    const validPassword = await bcrypt.compare(password, parent.password)
    if (validPassword){
        const token = createToken(parent.parent_id)
        res.status(200).json({
            "status": true,
            "message": "Login successful",
            "userId": parent.parent_id,
            "data":  parent.parent_name,
            "token": token
    }) 
    }
    else{
        res.status(400).json({
            "status": false,
            "message": "Invalid password",
        })
    }
}