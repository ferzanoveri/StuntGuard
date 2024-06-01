const prisma = require("../prisma/prisma")
const bcrypt = require("bcrypt")
const jwt = require("jsonwebtoken")

const createToken = (payload) => {
    return jwt.sign({ payload }, process.env.SECRET_KEY, {
        expiresIn: 30 * 24 * 60 * 60
    })
}

exports.register = async (req, res) => {

    const { parent_name, parent_gender, email, phone, password, confirmPassword } = req.body

    if (password !== confirmPassword) {
        return res.status(400).json({
            "status": false,
            "message": "Passwords do not match",
        })
    }

    // Validate gender
    if (parent_gender !== "male" && parent_gender !== "female") {
        return res.status(400).json({
            "status": false,
            "message": "Gender must be either 'male' or 'female'",
        });
    }
    // Convert gender to boolean
    const genderBoolean = parent_gender === "male";

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
                parent_gender: genderBoolean,
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
        console.log(error)
        res.status(500).json({
            "status": false,
            "message": "An unexpected error occurred on the server",
        })
    }
}

exports.getUsers = async (req, res) => {
    const parents = await prisma.parent.findMany()
    res.status(200).json({
        "status": true,
        "message": "All users",
        "data": parents,
    })
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
