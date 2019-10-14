import * as React from "react";
import { hot } from "react-hot-loader";

const logo = require("./../assets/img/react_logo.svg");
import "./../assets/scss/App.scss";
import ExchangeProfitAnalyzer from "./ExchangeProfitAnalyzer";

import DatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";
import axios from 'axios';

class App extends React.Component {

    state = {
        startDate: new Date(),
        persons: []
    };

    componentDidMount() {
      axios.get(`https://jsonplaceholder.typicode.com/users`)
        .then(res => {
          const persons = res.data;
          this.setState({ persons });
        })
    }

    handleChange = date => {
      this.setState({
        startDate: date
      });
    };

    public render() {
        return (
      <div className="App">
        <div className="App-header">
          <img src={logo} className="App-logo" alt="logo" />
          <h2>Exchange Profit Calculator</h2>
        </div>
        <ExchangeProfitAnalyzer/>
       </div>
        );
    }
}

declare let module: object;

export default hot(module)(App);
