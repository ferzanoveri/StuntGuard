const { where } = require("@tensorflow/tfjs-node");
const prisma = require("../prisma/prisma")

exports.addChild = async (req, res) => {

    const { child_name, child_gender, born_date, born_weight, born_height, breastfeeding } = req.body

    // Validate gender
    if (child_gender !== "Male" && child_gender !== "Female") {
        return res.status(400).json({
            "status": false,
            "message": "Gender must be either 'male' or 'female'",
        });
    }
    // Convert gender to boolean
    const genderBoolean = child_gender === "Male";

    // Validate ASI
    if (breastfeeding !== "Yes" && breastfeeding !== "No") {
        return res.status(400).json({
            "status": false,
            "message": "breastfeeding must be 'Yes' or 'No'",
        });
    }
    // Convert gender to boolean
    const breastfeedingBoolean = breastfeeding === "Yes";

    try {
        const child_age = calculateChildAge(born_date);
        const { parent_id } = req.params
        const newChild = await prisma.child.create({
            data: {
                parent_id: parent_id,
                child_name: child_name,
                child_gender: genderBoolean,
                born_date: born_date,
                child_age: child_age, 
                born_weight: born_weight,
                born_height: born_height,
                breastfeeding: breastfeedingBoolean,
            },
        }
        );
        res.status(201).json({
            status: true,
            message: "Child added successfully.",
            data: newChild,
        });

    } catch (error) {
        console.log(error)
        res.status(500).json({
            "status": false,
            "message": "An unexpected error occurred on the server",
        })
    }
};

// Function to calculate child age based on born date
function calculateChildAge(birthDate) {
    const today = new Date();
    const birth = new Date(birthDate);
    let ageMonths = (today.getFullYear() - birth.getFullYear()) * 12;
    ageMonths += today.getMonth() - birth.getMonth();

    if (today.getDate() < birth.getDate()) {
        ageMonths--;
    }

    return ageMonths;
}

exports.getChilds = async (req, res) => {
    try {
        const childs = await prisma.child.findMany();
        res.status(200).json({
            status: true,
            message: "All children retrieved successfully.",
            data: childs,
        });
    } catch (error) {
        console.error(error);
        res.status(500).json({
            status: false,
            message: "An unexpected error occurred on the server.",
        });
    }
};

exports.getParentChilds = async (req, res) => {
    const { parent_id } = req.params;

    try {
        const childs = await prisma.child.findMany({
            where: {
                parent_id: parent_id,
            },
        });

        res.status(200).json({
            status: true,
            message: "All children retrieved successfully.",
            data: childs,
        });
    } catch (error) {
        console.error(error);
        res.status(500).json({
            status: false,
            message: "An unexpected error occurred on the server.",
        });
    }
};

exports.getChildbyId = async (req, res) => {
    const { child_id } = req.params;

    try {
        const child = await prisma.child.findUnique({
            where: {
                child_id: child_id,
            },
        });

        if (!child) {
            return res.status(404).json({
                status: false,
                message: "Child not found.",
            });
        }

        res.status(200).json({
            status: true,
            message: "Child found successfully.",
            data: child,
        });
    } catch (error) {
        console.error(error);
        res.status(500).json({
            status: false,
            message: "An unexpected error occurred on the server.",
        });
    }
};

exports.removeChild = async (req, res) => {
    const { child_id } = req.params;

    try {
        const deletedChild = await prisma.child.delete({
            where: {
                child_id: child_id,
            },
        });

        res.status(200).json({
            status: true,
            message: "Child deleted successfully.",
            data: deletedChild,
        });
    } catch (error) {
        console.error(error);
        res.status(500).json({
            status: false,
            message: "An unexpected error occurred on the server.",
        });
    }
};

exports.updateChild = async (req, res) => {
    const { child_name, child_gender, born_date, born_weight, born_height, breastfeeding } = req.body;
    const { child_id } = req.params;
    const child_age = calculateChildAge(born_date);

    // Convert gender to boolean
    const genderBoolean = child_gender === "Male";
    const breastfeedingBoolean = breastfeeding === "Yes";

    try {

        const updatedChild = await prisma.child.update({
            where: {
                child_id: child_id,
            },
            data: {
                child_name: child_name,
                child_gender: genderBoolean,
                born_date: born_date,
                child_age: child_age,
                born_weight: born_weight,
                born_height: born_height,
                breastfeeding: breastfeedingBoolean,
            },
        });

        res.status(200).json({
            status: true,
            message: "Child updated successfully.",
            data: updatedChild,
        });
    } catch (error) {
        console.error(error);
        res.status(500).json({
            status: false,
            message: "An unexpected error occurred on the server.",
        });
    }
};