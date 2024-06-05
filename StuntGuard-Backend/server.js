const express = require('express');
const app = express();
const bodyParser = require('body-parser');
const App = require('./routes/routes')
const PORT = process.env.PORT
const prisma = require("./prisma/prisma")
const { updateChildAges } = require('./controller/childController')
const cron = require('node-cron');

app.use(bodyParser.urlencoded({ extended: true }));
app.use(bodyParser.json());

// Logika cron job untuk memanggil fungsi updateChildAges setiap hari pada jam 00:00
cron.schedule('0 0 * * *', async () => { // Menjalankan setiap hari pada jam 00:00
    await updateChildAges(); // Panggil fungsi updateChildAges
});

app.use(App)
app.listen(PORT, () =>
    console.log(`Server running on http://localhost:${PORT}`)
);