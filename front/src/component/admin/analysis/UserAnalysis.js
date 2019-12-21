//import moment from 'moment';
import axios from 'axios';
import React, { Component } from 'react';
import UserMonthsChart from './UserMonthsChart';
import UserSexAndAgeChart from './UserSexAndAgeChart';
import UserYearsChart from './UserYearsChart';
import UserAllChart from './UserAllChart';
import LoginMonthsChart from './LoginMonthsChart';
import WithdrawReasonChart from './WithdrawReasonChart';
import LoginYearsChart from './LoginYearsChart';
import LoginSexAndAgeChart from './LoginSexAndAgeChart';

class UserAnalysis extends Component {
    state = {
        data: null
    }


    getData = async () => {
        try {

            const response = await axios.get('/usersAnalysis');

            this.setState({
                data:response.data
            });
        } catch (e) {
            console.log(e);
        }
    }

    componentDidMount() {
        //첫 로딩시에 getData 호출
        this.getData();
    }

    render() {
        const {data}=this.state;
        
        return (
            <div>
                {data &&
               (
                <div>
                <LoginSexAndAgeChart data={data.loginSexAndAge} select={data.yearMonthsOfLogin}></LoginSexAndAgeChart>
                <LoginMonthsChart data={data.loginMonth} select={data.yearsOfLogin}></LoginMonthsChart>
                <LoginYearsChart data={data.loginYear}></LoginYearsChart>
                <UserSexAndAgeChart data={data.userSexAndAge} select={data.yearMonthsOfUsers}></UserSexAndAgeChart>
                <UserMonthsChart data={data.userMonth} select={data.yearsOfUsers}></UserMonthsChart>
                <UserYearsChart data={data.userYear}> </UserYearsChart>
                <UserAllChart data={data.allUsers}></UserAllChart>
                <WithdrawReasonChart data={data.withdrawReasons} code={data.allWithdrawReasons} select={data.yearsOfWithdraws}></WithdrawReasonChart>
                </div>
                )}
                
            </div>
        );
    }
}



export default UserAnalysis;
