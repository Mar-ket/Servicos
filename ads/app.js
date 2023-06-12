require('dotenv').config();
const express = require("express");
const cors = require("cors");
const app = express();

const path = require("path");
const ejs = require("ejs");
const axios = require("axios");



app.use(cors());

app.use(express.json());

//DB Connection
const conn = require("./db/conn")
conn();

// Routes
const routes = require("./routes/router");
app.use("/ads", routes);

app.set("views", path.join(__dirname, "views")  );
app.set("view engine", "ejs");

app.get("/ads", async (req, res) => {
  try {
    const response1 = await axios.get("http://ads:8000/ads/random");
    const response2 = await axios.get("http://ads:8000/ads/random");

    const ads = [response1.data, response2.data];

    res.json({ ads });
  } catch (error) {
    console.error(error);
    res.status(500).send("Error fetching random ads");
  }
});


app.listen(process.env.PORT, '0.0.0.0', function () {
  console.log(`Server Online on port ${process.env.PORT}!`);
});


