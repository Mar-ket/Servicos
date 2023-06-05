const router = require("express").Router();

const adController = require("../controllers/AdController");

router
    .route("/create")
    .post((req, res) => adController.create(req, res));

router
    .route("/showAll")
    .get((req, res) => adController.getAll(req, res));

router
    .route("/random")
    .get((req, res) => adController.getRandom(req, res));

router
    .route("/:id")
    .get((req, res) => adController.get(req, res));

router
    .route("/delete/:id")
    .delete((req, res) => adController.delete(req, res));

router
    .route("/update/:id")
    .put((req, res) => adController.update(req, res));

router
    .route("/click/:id")
    .post((req, res) => adController.incrementClicks(req, res));

router
    .route("/stats/:id/")
    .get((req, res) => adController.getStats(req, res));




module.exports = router;