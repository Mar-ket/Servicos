const { Ad: AdModel } = require("../models/Ad");

const adController = {

    create: async(req, res) => {
        try{
            const ad = {
                title: req.body.title,
                description: req.body.description,
                imageUrl: req.body.imageUrl,
                targetUrl: req.body.targetUrl,
                status: req.body.status || "active"
            };

            const response = await AdModel.create(ad);

        res.status(201).json({response, msg: "Ad created with success!" });

        }catch (error){
            console.log(error)
        }
    },

    getAll: async (req, res) => {
        try {
            const { status } = req.query; 
            const query = status ? { status } : {};
            const ads = await AdModel.find(query); 
            res.json(ads);
        } catch (error) {
            console.log(error);
        }
    },

    get: async(req, res) => {
        try {
            const id = req.params.id
            const ad = await AdModel.findById(id);

            if(!ad){
                res.status(404).json({msg: "Ad not found!"});
            }

            res.json(ad);

        } catch (error) {
            console.log(error)
        }
    },
    delete: async(req, res) => {
        try {
            const id = req.params.id
            const ad = await AdModel.findById(id);

            if(!ad){
                res.status(404).json({msg: "Ad not found!"});
            }

            const deleteAd = await AdModel.findByIdAndDelete(id)

            res.status(200).json({deleteAd, msg: "Ad deleted with success!"});

        } catch (error) {
            console.log(error)
        }
    },

    update: async(req, res) => {
        try {
            const id = req.params.id
            const ad = {
                title: req.body.title,
                description: req.body.description,
                imageUrl: req.body.imageUrl,
                targetUrl: req.body.targetUrl,
                status: req.body.status,
            };

            const updatedAd = await AdModel.findByIdAndUpdate(id, ad)

            if(!updatedAd){
                res.status(404).json({msg: "Ad not found!"});
            }

            res.status(200).json({updatedAd, msg: "Ad updated with success!"});

        } catch (error) {
            console.log(error)
        }
    },

    incrementClicks: async (req, res) => {
        try {
          const id = req.params.id;
          const ad = await AdModel.findById(id);
      
          if (!ad) {
            res.status(404).json({ msg: "Ad not found!" });
          } else {
            ad.clicks += 1;
            await ad.save();
      
            res.status(200).json({ msg: "Click registered successfully!" });
          }
        } catch (error) {
          console.log(error);
          res.status(500).json({ msg: "Error registering click." });
        }
      },

    getStats: async (req, res) => {
        try {
          const id = req.params.id;
          const ad = await AdModel.findById(id, 'clicks');
      
          if (!ad) {
            res.status(404).json({ msg: "Ad not found!" });
          } else {
            res.status(200).json({ clicks: ad.clicks });
          }
        } catch (error) {
          console.log(error);
          res.status(500).json({ msg: "Error retrieving ad stats." });
        }
    },
    
    getRandom: async (req, res) => {
        try {
            const count = await AdModel.countDocuments();
            const random = Math.floor(Math.random() * count);
            const ad = await AdModel.findOne().skip(random);
  
        if (!ad) {
            res.status(404).json({ msg: "No ads found!" });
        } else {
            res.json(ad);
        }
        } catch (error) {
            console.log(error);
            res.status(500).json({ msg: "Error fetching random ad." });
        }
    },
  

};

module.exports = adController;


