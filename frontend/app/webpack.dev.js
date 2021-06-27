const path = require('path');
const HtmlWebPackPlugin = require('html-webpack-plugin');
const CopyPlugin = require("copy-webpack-plugin");

module.exports = {
  output: {
    path: path.resolve(__dirname, 'dist'),
    filename: 'bundle.js',
  },
  resolve: {
    modules: [path.join(process.cwd(), 'external/frapp_modules/node_modules')],
  },
  entry : path.join(process.cwd(), "frontend/app/src/index.js"),
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
    }),
     new CopyPlugin({
      patterns: [
        { from: path.join(process.cwd(), "proto/task/task_nodejs_service_proto_pb/proto/task/*.js"), to: path.join(process.cwd(), "frontend/app/src/") },
      ],
    }),
  ],
};