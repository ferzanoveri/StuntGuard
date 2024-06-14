const prisma = require("../prisma/prisma")

exports.addChild = async (req, res) => {

    const { child_name, child_gender, birth_date, birth_weight, birth_height, breastfeeding } = req.body

    // Validate gender
    if (child_gender !== "Male" && child_gender !== "Female") {
        return res.status(400).json({
            "status": false,
            "message": "Gender must be either 'Male' or 'Female'",
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
        const child_age = calculateChildAge(birth_date);
        // Validate age
        if (child_age >= 60) {
            return res.status(400).json({
                status: false,
                message: "child's age must be under 5 years (60 months)",
            });
        }
        const { parent_id } = req.params
        const newChild = await prisma.child.create({
            data: {
                parent_id: parent_id,
                child_name: child_name,
                child_gender: genderBoolean,
                birth_date: birth_date,
                child_age: child_age,
                birth_weight: parseFloat(birth_weight),
                birth_height: parseFloat(birth_height),
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
        console.error(error)
        res.status(500).json({
            "status": false,
            "message": "An unexpected error occurred on the server",
        })
    }
};

// Function to calculate child age based on birth date
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

async function updateChildAges() {
    try {
        const children = await prisma.child.findMany();
        const today = new Date();

        for (const child of children) {
            const birthDate = new Date(child.birth_date);
            
            // Perbarui usia anak jika tanggal lahirnya sama dengan tanggal hari ini
            if (today.getDate() === birthDate.getDate() && today.getMonth() === birthDate.getMonth()) {
                const newAge = calculateChildAge(child.birth_date);
                await prisma.child.update({
                    where: { child_id: child.child_id },
                    data: { child_age: newAge }
                });
            }
        }
        console.log('Child ages updated successfully.');
    } catch (error) {
        console.error('Error updating child ages:', error);
    }
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
        const parent = await prisma.parent.findUnique({
            where: { 
                parent_id: parent_id 
            }
        });

        if (!parent) {
            return res.status(404).json({
                status: false,
                message: 'Parent not found.',
            });
        }
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
    const { child_name, child_gender, birth_date, birth_weight, birth_height, breastfeeding } = req.body;
    const { child_id } = req.params;

    try {
        // Check if the child exists
        const existingChild = await prisma.child.findUnique({
            where: { child_id: child_id },
        });

        if (!existingChild) {
            return res.status(404).json({
                status: false,
                message: "Child not found.",
            });
        }

        // Prepare updated data
        const updatedData = {};
        if (child_name !== undefined) updatedData.child_name = child_name;
        if (child_gender !== undefined) {
            if (child_gender !== "Male" && child_gender !== "Female") {
                return res.status(400).json({
                    status: false,
                    message: "Gender must be either 'male' or 'female'",
                });
            }
            updatedData.child_gender = child_gender === "Male";
        }
        if (birth_date !== undefined) {
            updatedData.birth_date = birth_date;
            // Calculate new age based on birth date
            const newAge = calculateChildAge(birth_date);
            if (newAge >= 60) {
                return res.status(400).json({
                    status: false,
                    message: "Child's age must be under 5 years (60 months)",
                });
            }
            updatedData.child_age = newAge;
        }
        if (birth_weight !== undefined) updatedData.birth_weight = parseFloat(birth_weight);
        if (birth_height !== undefined) updatedData.birth_height = parseFloat(birth_height);
        if (breastfeeding !== undefined) {
            if (breastfeeding !== "Yes" && breastfeeding !== "No") {
                return res.status(400).json({
                    status: false,
                    message: "Breastfeeding must be 'Yes' or 'No'",
                });
            }
            updatedData.breastfeeding = breastfeeding === "Yes";
        }

        // Update child data
        const updatedChild = await prisma.child.update({
            where: { child_id: child_id },
            data: updatedData,
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
