const prisma = require('../prisma/prisma')
const bcrypt = require("bcrypt")

exports.getUsers = async (req, res) => {
    const parents = await prisma.parent.findMany()
    res.status(200).json({
        "status": true,
        "message": "All users",
        "data": parents,
    })
}

exports.getUserbyId = async (req, res) => {
    const { parent_id } = req.params;

    try {
        const parent = await prisma.parent.findUnique({
            where: {
                parent_id: parent_id,
            },
        });

        if (!parent) {
            return res.status(404).json({
                status: false,
                message: "parent not found.",
            });
        }

        res.status(200).json({
            status: true,
            message: "parent found successfully.",
            data: parent,
        });
    } catch (error) {
        console.error(error);
        res.status(500).json({
            status: false,
            message: "An unexpected error occurred on the server.",
        });
    }
};

exports.removeUser = async (req, res) => {
    const { parent_id } = req.params;

    try {
        const deletedparent = await prisma.parent.delete({
            where: {
                parent_id: parent_id,
            },
        });

        res.status(200).json({
            status: true,
            message: "parent deleted successfully.",
            data: deletedparent,
        });
    } catch (error) {
        console.error(error);
        res.status(500).json({
            status: false,
            message: "An unexpected error occurred on the server.",
        });
    }
};

exports.updateUser = async (req, res) => {
    const { parent_name, parent_gender, email, phone, password, confirmPassword } = req.body;
    const { parent_id } = req.params;
    if (password !== confirmPassword) {
        return res.status(400).json({
            "status": false,
            "message": "Passwords do not match",
        })
    }
    // Convert gender to boolean
    const genderBoolean = parent_gender === "Male";
    //encrypt the password
    const salt = await bcrypt.genSalt();
    const hashedPassword = await bcrypt.hash(password, salt)
    try {
        const updatedparent = await prisma.parent.update({
            where: {
                parent_id: parent_id,
            },
            data: {
                parent_name: parent_name,
                parent_gender: genderBoolean,
                email: email,
                phone: phone,
                password: hashedPassword
            },
        });

        res.status(200).json({
            status: true,
            message: "parent updated successfully.",
            data: updatedparent,
        });
    } catch (error) {
        console.error(error);
        res.status(500).json({
            status: false,
            message: "An unexpected error occurred on the server.",
        });
    }
};