import axios from 'axios';
import React, { Component } from 'react';
import OneLineChart from './chart/OneLineChart';

class LoginMonthsChart extends Component {
    state = {
        title: '년 월별 로그인 수',
        data: [],
        selected:'',
        label:['로그인']
    }

    handleChange = (e) => {
        this.setState({
            selected:e.target.value
        })
    }

    getData = async () => {
        const {selected}=this.state;
        try {

            const response = await axios.get(`/usersAnalysis/user/month/${selected}`);
            
            const data = response.data.userMonth.map(
                (candle) => ({
                    label: candle[0],
                    col1: candle[1]
                })
            ); 
            
            
            this.setState({
                data
            });
        } catch (e) {
            console.log(e);
        }
    }

    componentDidMount() {
        const data = this.props.data.map(
            (candle) => ({
                label: candle[0],
                col1: candle[1]
            })
        );
        this.setState({
            data,
            selected:this.props.select[0]
        })
    }

    componentDidUpdate(prevProps, prevState) {
        if (prevState.selected && prevState.selected !== this.state.selected) {
            this.getData();
        }
    }

    render() {
        const {selected,data,title,label}=this.state;
        const yearOp=this.props.select.map(y=>(<option key={y} value={y}>{y}</option>));
        return (
            <div>
                <select value={selected} onChange={this.handleChange}>
                    {yearOp}
                </select>
                {data.length > 0 && <OneLineChart data={data} title={title} label={label} selected={selected} />}
            </div>
        );
    }
}



export default LoginMonthsChart;
