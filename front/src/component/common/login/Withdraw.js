import React, { Component } from 'react';
import axios from 'axios';

class Withdraw extends Component {

    state={
        code:[],
        selectedCode:''
    }

    componentDidMount(){
        this.getCode();
    }

    async getCode(){
        const response=await axios.get('/withdraw');
        console.log(response)
        this.setState({
            code:response.data
        })
    }

    withdraw =async()=>{
        const params = new URLSearchParams();
        params.append("code", this.state.selectedCode);
        params.append("userId",sessionStorage.getItem("userId"));
        const response = await axios({
            method: 'post',
            url: '/withdrawProcess',
            data: params
        });
        sessionStorage.removeItem("userId");
        this.props.history.push('/');
    }

    handleSelect=(e)=>{
        this.setState({
            selectedCode:e.target.value
        })
        
    }

    render() {
        const {selectedCode, code} = this.state;
        const {handleSelect, withdraw}=this;
        const codeOp=code.map(c=>(<option key={c.code} value={c.code}>{c.content}</option>))
        return (
            <div>
                <select value={selectedCode} onChange={handleSelect}>
                    <option value="-1">탈퇴 사유 선택</option>
                    {codeOp}
                </select>
            <button onClick={withdraw}>탈퇴하기</button>
            </div>

        );
    }

}

export default Withdraw;

