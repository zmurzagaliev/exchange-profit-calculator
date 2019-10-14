import * as React from "react";
import { hot } from "react-hot-loader";
import DatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";
import "./../assets/scss/ExchangeProfitAnalyzer.scss";
import * as moment from 'moment'
import { Button, Form, FormGroup, Label, Input, FormText } from 'reactstrap';
import axios from 'axios';

class ExchangeProfitAnalyzer extends React.Component {

    state = {
        buyDate: new Date(),
        amount: 0,
        profit: 0
    };

    handleBuDateChange = date => {
      this.setState({
        buyDate: date
      });
    };

    handleAmountChange = amnt => {
      this.setState({
        amount: amnt.target.value
      });
    };

      callApi(){
          axios.get('http://localhost:8081/exchange/v1/calculate'
            {params:{amount: this.state.amount, buyDate: moment(this.state.startDate).format('YYYY-MM-DD')}}
          ).then(response => this.setState({profit: response.data}))
      }

    public render() {
        return (
           <Form className="calcForm">
             <FormGroup>
               <Label for="amount">Amount</Label>
               <Input type="text" id="amount"  className="form-control"  pattern="[0-9]*" name="amount" value={this.state.amount}
                  onChange={this.handleAmountChange} placeholder="amount" />
             </FormGroup>
             <FormGroup>
               <Label for="buDate">Buy Date</Label>
               <DatePicker
                  className="form-control"
                  id="buDate"
                  selected={this.state.buyDate}
                  onChange={this.handleBuDateChange}/>
             </FormGroup>
             <FormGroup>
               <Label for="profit">Profit</Label>
               <Input type="text" readOnly id="profit" value={this.state.profit}  placeholder="profit" />
             </FormGroup>
             <Button onClick={() => this.callApi()} className="btn btn-primary">Calculate</Button>
           </Form>
        );
  }
}

declare let module: object;
export default hot(module)(ExchangeProfitAnalyzer);