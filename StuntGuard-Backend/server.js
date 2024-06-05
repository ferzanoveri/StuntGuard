const express = require('express');
const app = express();
const bodyParser = require('body-parser');
const App = require('./routes/routes')
const PORT = process.env.PORT

app.use(bodyParser.urlencoded({ extended: true }));
app.use(bodyParser.json());

app.use(App)
app.listen(PORT, () =>
    console.log(`Server running on http://localhost:${PORT}`)
);