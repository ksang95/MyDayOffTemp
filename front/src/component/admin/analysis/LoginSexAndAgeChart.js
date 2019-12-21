import axios from 'axios';
import React, { Component } from 'react';
import TwoBarChart from './TwoBarChart';

class LoginSexAndAgeChart extends Component {
    state = {
        title: '월 연령대별 남녀 로그인 수',
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

            const response = await axios.get(`/usersAnalysis/user/sexAndAge/${selected}`);

            const data = response.data.userSexAndAge.map(
                (candle) => ({
                    sex: candle[0],
                    age: candle[1],
                    col1: candle[2]
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
                sex: candle[0],
                age: candle[1],
                col1: candle[2]
            })
            );
            console.log(data);
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
        const yearMonthOp=this.props.select.map(y=>(<option key={y} value={y}>{y}</option>));
        return (
            <div>
                 <select value={selected} onChange={this.handleChange}>
                    {yearMonthOp}
                </select>
                {data.length > 0 && <TwoBarChart data={data} title={title} label={label} selected={selected&&selected.substring(selected.indexOf('-')+1)} />}
            </div>
        );
    }
}



export default LoginSexAndAgeChart;
