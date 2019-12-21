import axios from 'axios';
import React, { Component } from 'react';
import DoughnutChart from './DoughnutChart';

class WithdrawReasonChart extends Component {
    state = {
        title: '년 탈퇴 사유',
        data: [],
        selected:'',
        label:[]
    }

    handleChange = (e) => {
        this.setState({
            selected:e.target.value
        })
    }

    getData = async () => {
        const {selected}=this.state;
        try {

            const response = await axios.get(`/usersAnalysis/user/withdrawReasons/${selected}`);
            
            const data=response.data.withdrawReasons.map(element => {
                let d={};
                d.label=element[0];
                for (let index = 1; index < this.state.size; index++) {
                    d["col"+index]=element[index];
                }
                return d;
            });
            
            this.setState({
                data
            });
        } catch (e) {
            console.log(e);
        }
    }

    componentDidMount() {
        const reasons=this.props.code.map(c=>c.content);
        const size=Object.keys(reasons).length;
        const data=this.props.data.map(d=>d[1]);
        console.log(data);
        this.setState({
            data,
            selected:this.props.select[0],
            label:reasons,
            size:size
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
                {data.length > 0 && <DoughnutChart data={data} title={title} label={label} selected={selected} />}
            </div>
        );
    }
}



export default WithdrawReasonChart;
