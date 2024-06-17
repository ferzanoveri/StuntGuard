const prisma = require('../prisma/prisma')
const bcrypt = require("bcrypt")

exports.getUsers = async (req, res) => {
    try {
        const parents = await prisma.parent.findMany();
        res.status(200).json({
            status: true,
            message: "All users",
            data: parents,
        });
    } catch (error) {
        console.error("Error retrieving users:", error);
        res.status(500).json({
            status: false,
            message: "An unexpected error occurred while retrieving users.",
        });
    }
};

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
        const parent = await prisma.parent.findUnique({
            where: { parent_id: parent_id }
        });

        if (!parent) {
            return res.status(404).json({
                status: false,
                message: 'Parent not found.',
            });
        }

        await prisma.$transaction(async (transaction) => {
            // Get all children of the parent
            const children = await transaction.child.findMany({
                where: { parent_id: parent_id },
                select: { child_id: true },
            });

            const childIds = children.map(child => child.child_id);

            // Delete related records in the FoodDetails table
            await transaction.foodDetails.deleteMany({
                where: {
                    recommendation_id: {
                        in: (await transaction.recommendation.findMany({
                            where: { child_id: { in: childIds } },
                            select: { recommendation_id: true },
                        })).map(rec => rec.recommendation_id),
                    },
                },
            });

            // Delete related records in the Recommendation table
            await transaction.recommendation.deleteMany({
                where: {
                    child_id: { in: childIds },
                },
            });

            // Delete related records in the StuntPredict table
            await transaction.stuntPredict.deleteMany({
                where: {
                    child_id: { in: childIds },
                },
            });

            // Delete the children records
            await transaction.child.deleteMany({
                where: {
                    parent_id: parent_id,
                },
            });

            // Finally, delete the parent record
            await transaction.parent.delete({
                where: {
                    parent_id: parent_id,
                },
            });
        });

        res.status(200).json({
            status: true,
            message: "Parent deleted successfully.",
        });
    } catch (error) {
        console.error(error);
        res.status(500).json({
            status: false,
            message: "An unexpected error occurred on the server.",
        });
    } finally {
        await prisma.$disconnect();
    }
};

exports.updateUser = async (req, res) => {
    const { parent_name, email, phone } = req.body;
    const { parent_id } = req.params;

    try {
        const parent = await prisma.parent.findUnique({
            where: { parent_id: parent_id }
        });

        if (!parent) {
            return res.status(404).json({
                status: false,
                message: 'Parent not found.',
            });
        }

        // Prepare updated data
        const updatedData = {};
        if (parent_name !== undefined) updatedData.parent_name = parent_name;
        if (email !== undefined) updatedData.email = email;
        if (phone !== undefined) updatedData.phone = phone;

        // Update parent data
        const updatedParent = await prisma.parent.update({
            where: { parent_id: parent_id },
            data: updatedData,
        });

        res.status(200).json({
            status: true,
            message: "Parent updated successfully.",
            data: updatedParent,
        });
    } catch (error) {
        console.error(error);
        res.status(500).json({
            status: false,
            message: "An unexpected error occurred on the server.",
        });
    }
};


exports.updatePassword = async (req, res) => {
    const { oldPassword, newPassword, confirmPassword } = req.body;
    const { parent_id } = req.params;

    try {
        // Fetch parent data from the database
        const parent = await prisma.parent.findUnique({
            where: { parent_id: parent_id }
        });

        if (!parent) {
            return res.status(404).json({
                status: false,
                message: 'Parent not found.',
            });
        }

        // Validate old password
        const validPassword = await bcrypt.compare(oldPassword, parent.password);
        if (!validPassword) {
            return res.status(400).json({
                status: false,
                message: 'Invalid old password.',
            });
        }

        // Check if new passwords match
        if (newPassword !== confirmPassword) {
            return res.status(400).json({
                status: false,
                message: 'New passwords do not match.',
            });
        }

        // Encrypt the new password
        const salt = await bcrypt.genSalt();
        const hashedPassword = await bcrypt.hash(newPassword, salt);

        // Update password in the database
        const updatedParent = await prisma.parent.update({
            where: { parent_id: parent_id },
            data: {
                password: hashedPassword,
            },
        });

        res.status(200).json({
            status: true,
            message: 'Password updated successfully.',
            data: updatedParent,
        });
    } catch (error) {
        console.error(error);
        res.status(500).json({
            status: false,
            message: 'An unexpected error occurred on the server.',
        });
    }
};