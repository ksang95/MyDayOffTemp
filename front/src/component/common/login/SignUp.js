import React, { Component } from 'react';
import queryString from 'query-string';
import axios from 'axios';
import SignUpForm from './SignUpForm';

class SignUp extends Component {

    state = {
        users: null,
        signUp: false,
        error: null
    }


    componentDidMount() {
        const path = this.props.location.pathname;
        const socialType = path.substring(path.lastIndexOf('/') + 1);
        const query = queryString.parse(this.props.location.search);
        const params = new URLSearchParams();
        params.append("code", query.code);
        params.append("socialType", socialType);

        this.tryLogin(params);
    }

    tryLogin = async (params) => {
        const response = await axios({
            method: 'post',
            url: '/login',
            data: params
        }).then(success=>{

            let users = success.data;
            if (!users.id) {
                
                users = {
                    socialId: users.socialId,
                    name: users.name,
                    phone: users.phone,
                    birth: users.birth,
                    sex: !users.sex ? 'f' : '',
                    height: users.height,
                    weight: users.weight,
                    accessToken: users.accessToken,
                    refreshToken: users.refreshToken
                }
                for (let u of Object.keys(users)) {
                    users[u] = users[u] ? users[u] : '';
                }
                this.setState({
                    users: users
                });
            }else{
                sessionStorage.setItem("userId",users.id);
                const url=document.referrer;
                this.props.history.push(url.substring(url.lastIndexOf(":")+5));
            }
        });

    }

    handleChange = (e) => {
        this.setState({
            users: {
                ...this.state.users,
                [e.target.name]: e.target.value
            }
        })
    }

    handleClick = async () => {
        let flag = true;
        const users = this.state.users;
        for (let key of Object.keys(users)) {
            console.log(key);
            if (!(key === 'height' || key === 'weight') && (users[key].length === 0)) {
                flag = false;
                break;
            }
        }
        if (flag) {

            const { users } = this.state;
            //users.phone = users.phone.replace(/-/gi, "");
            const params = new URLSearchParams();
            for (let u of Object.keys(users)) {
                params.append(u, users[u]?users[u]:null);
            }
            await axios.post('/signUp', users)
                .then(success => {
                    console.log(this.state.users);
                    this.setState({
                        users:null,
                        signUp:true
                    });
                    sessionStorage.setItem("userId",users.id);
                });

        } else {
            this.setState({
                error: '모든 항목을 입력해주세요.'
            });
        }
    }

    handleExit = (e) => {
        // this.setState({
        //     signUp:false
        // })
        const url=document.referrer;
        this.props.history.push(url.substring(url.lastIndexOf(":")+5));
    }


    render() {
        const { users, signUp, error } = this.state;
        const { handleChange, handleClick, handleExit } = this;
        const message = signUp ? (<div><div>가입을 환영합니다!</div>회원가입 기념 5% 할인 쿠폰이 쿠폰함으로 지급되었습니다. 쿠폰함을 확인하세요!<button onClick={handleExit}>확인</button></div>) : undefined;
        
        if (!users) {
            return (
                <div>
                    {message}
                </div>
            );
        }else{

            return (
                <SignUpForm users={users} onChange={handleChange} onClick={handleClick} error={error}></SignUpForm>
            );
        }
    }
}

export default SignUp;

