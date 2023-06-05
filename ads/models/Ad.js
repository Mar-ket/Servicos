const mongoose = require("mongoose");

const { Schema } = mongoose;

const adSchema = new mongoose.Schema({
    title: {
        type: String,
        required: true,
        trim: true,
    },
    description: {
        type: String,
        required: true,
        trim: true,
    },
    imageUrl: {
        type: String,
        required: true,
        trim: true,
    },
    targetUrl: {
        type: String,
        required: true,
        trim: true,
    },
    
    clicks: {
        type: Number,
        default: 0,
    },
    status: {
        type: String,
        enum: ["active", "paused", "archived"],
        default: "active",
      },
    
    
},
  {timestamps: true}
);

const Ad = mongoose.model('Ad', adSchema);

module.exports = {
    Ad,
    adSchema,
}