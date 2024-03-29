import React, { Component } from 'react';
import axios from 'axios';

class Withdraw extends Component {

    state = {
        code: [],
        selectedCode: '',
        error: ''
    }

    componentDidMount() {
        this.getCode();
    }

    async getCode() {
        const response = await axios.get('/withdraw');
        console.log(response)
        this.setState({
            code: response.data
        })
    }

    withdraw = async () => {
        const params = new URLSearchParams();
        const code = this.state.code[this.state.selectedCode];
        if (code) {

            params.append("code", code);
            params.append("userId", sessionStorage.getItem("userId"));
            const response = await axios({
                method: 'post',
                url: '/withdrawProcess',
                data: params
            });
            sessionStorage.removeItem("userId");
            this.props.history.push('/');
        } else {
            this.setState({
                error: '탈퇴 사유를 선택해주세요.'
            });
        }
    }

    handleSelect = (e) => {
        this.setState({
            selectedCode: e.target.value
        })

    }

    render() {
        const { selectedCode, code } = this.state;
        const { handleSelect, withdraw } = this;
        const codeOp = code.map((c, index) => (<option key={c.code} value={index}>{c.content}</option>))
        return (
            <div>
                <select value={selectedCode} onChange={handleSelect}>
                    <option value="-1">탈퇴 사유 선택</option>
                    {codeOp}
                </select>
                <div>{this.state.error}</div>
                <div><button onClick={withdraw}>탈퇴하기</button></div>
            </div>

        );
    }

}

export default Withdraw;

