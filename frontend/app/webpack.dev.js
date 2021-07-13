const path = require('path');
const HtmlWebPackPlugin = require('html-webpack-plugin');


module.exports = {
  mode: 'production',
  output: {
    path: path.resolve(__dirname, 'devDist'),
    filename: '[name].bundle.js',
  },
  resolve: {
    modules: [
      path.join(process.cwd(), 'external/frapp_modules/node_modules'),
     ],
  },
  entry : {
    index: [path.join(process.cwd(), "frontend/app/src/index.js")],
  },
  module: {
    rules: [
      {
        test: /\.(js|jsx)$/,
        exclude: /node_modules/,
        use: {
          loader: 'babel-loader',
        },
      },
      {
        test: /\.css$/,
        use: [
          {
            loader: 'style-loader',
          },
          {
            loader: 'css-loader',
          },
        ],
      },
    ],
  },
  plugins: [
    new HtmlWebPackPlugin({
      template: './frontend/app/src/index.html',
    })
  ],
};