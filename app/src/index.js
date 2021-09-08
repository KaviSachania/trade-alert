import React from 'react';
import ReactDOM from 'react-dom';
import './reset.css';
import 'bootstrap/dist/css/bootstrap.css';
import './index.css';
import './App.css';
import App from './App';
import reportWebVitals from './reportWebVitals';
import axios from "axios";


axios.defaults.baseURL = 'http://localhost:8080';
axios.defaults.headers.common['Access-Control-Allow-Origin'] = '*';
axios.defaults.withCredentials = true;
if (process.env.NODE_ENV === 'production') {
    axios.defaults.baseURL = 'https://tradealert.us';
    // axios.defaults.headers.common['Access-Control-Allow-Origin'] = '*';
    // axios.defaults.withCredentials = true;
}

ReactDOM.render(
  <React.StrictMode>
    <App />
  </React.StrictMode>,
  document.getElementById('root')
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
