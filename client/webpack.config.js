var path = require('path');
const HtmlWebPackPlugin = require("html-webpack-plugin");
module.exports = {
    mode: 'development',
    resolve: {
        alias: {
            "./images/layers.png$": path.resolve(
                __dirname,
                "./node_modules/leaflet/dist/images/layers.png"
            ),
            "./images/layers-2x.png$": path.resolve(
                __dirname,
                "./node_modules/leaflet/dist/images/layers-2x.png"
            ),
            "./images/marker-icon.png$": path.resolve(
                __dirname,
                "./node_modules/leaflet/dist/images/marker-icon.png"
            ),
            "./images/marker-icon-2x.png$": path.resolve(
                __dirname,
                "./node_modules/leaflet/dist/images/marker-icon-2x.png"
            ),
            "./images/marker-shadow.png$": path.resolve(
                __dirname,
                "./node_modules/leaflet/dist/images/marker-shadow.png"
            )
        }
    },
    module: {
        rules: [
            {
                test: /\.js$/,
                exclude: /node_modules/,
                use: {
                    loader: "babel-loader"
                }
            },
            {
                test: /\.html$/,
                use: [
                    {
                        loader: "html-loader"
                    }
                ]
            },
            {
                test: /\leaflet.css$/,
                use: [
                    {loader: "style-loader"},
                    {loader: "css-loader"}
                ]
            },
            {
                test: /\.css$/,
                exclude: /\leaflet.css$/,
                use: [
                    {loader: "style-loader"},
                    {
                        loader: "css-loader",
                        options: {
                            modules: true
                        }
                    }
                ]
            },
            {
                test: /\.(png|jpg|gif)$/,
                use: [
                    {
                        loader: 'file-loader',
                        options: {}
                    }
                ]
            }

        ]
    },
    plugins: [
        new HtmlWebPackPlugin({
            template: "./src/index.html",
            filename: "./index.html"
        })
    ],
    output: {
        path: path.resolve(__dirname, "build")
    }
};