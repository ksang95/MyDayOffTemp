import React, { Component } from 'react';
import Axios from 'axios';

export default class componentName extends Component {

    state = {
       
        invoice : "",
        tid : ""
        
        
    }


    updateInvoice(){
        Axios.post("/invoice",
             this.state
        ).then(res =>{
            this.setState({
            resultTid : res.data.tid,
            resultInvoice : res.data.invoice,
            invoice : '',
            tid : ''
        })
        })
    }
    handleChange = (e) =>{
        this.setState({
            [e.target.name] : e.target.value
        })
        
    }
  render() {
    return (
      <div> 
        송장번호 : <input type="text" onChange={this.handleChange} value={this.state.invoice} name="invoice"></input>   
        주문번호 : <input type="text" onChange={this.handleChange} value={this.state.tid} name="tid"></input>   
        <button onClick={this.updateInvoice.bind(this)}>송장등록</button>
        <br></br>
        {this.state.resultTid ? <span>주문번호 <b>{this.state.resultTid}</b>에 송장번호<b>{this.state.resultInvoice}</b>를 등록했습니다.</span> : ""}
        
     </div>
    );
  }
}
