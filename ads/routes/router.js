const router = require("express").Router();

const adsRouter = require("./ads");

router.use("/", adsRouter);



module.exports = router;