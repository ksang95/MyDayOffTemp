import axios from 'axios';
import React, { Component } from 'react';
import { Link } from 'react-router-dom';
import Login from '../../common/login/Login';

class Menu extends Component {
    state={
        login:false
    }

    componentDidMount(){
        if(sessionStorage.getItem("userId"))
       this.setState({
           login:true
       })
    }
    shouldComponentUpdate(prevProps, prevState) {
       
        return true;
    }

    handleLogin = (e) => {
        switch (e.target.innerHTML) {
            case '로그아웃':
                this.logout();
                break;
            case '로그인':
                document.getElementById("loginFrame").style.visibility = "visible";
                break;
        }
    }

    async logout2(){
        const res = await axios.get("/logout", {
        }
        )
        console.log(res.data)
        if(res.data===1){
            this.props.history.push("/login")
        }
    }

    logout = async () => {
        const params = new URLSearchParams();
        params.append("userId", sessionStorage.getItem("userId"));
        const response = await axios({
            method: 'post',
            url: '/logout',
            data: params
        }).then(success=>{
            sessionStorage.removeItem("userId");
            this.setState({
                login:false
            })
        });
    }

    render() {
        const login=sessionStorage.getItem("userId")?'로그아웃':'로그인';

        return (
            <div>

                <ul>
                    <li><div onClick={this.handleLogin}>{login}</div></li>
                    <li><Link to="/signUp">회원가입</Link></li>
                    <li><Link to="/admin/addProduct">상품 등록</Link></li>
                    <li><Link to="/admin/deleteProduct">상품 삭제</Link></li>
                    <li><Link to="/admin/updateInvoice">송장번호 등록</Link></li>
                    <li><Link to="/admin/userList">유저리스트</Link></li>
                    <li><Link to="/admin/usersAnalysis">회원 통계</Link></li>
                    <li><Link to="/withdraw">탈퇴하기</Link></li>
                    <li><button onClick={this.logout2.bind(this)}>로그아웃</button></li>

                </ul>
                <div className="loginFrame" id="loginFrame" style={{ visibility: "hidden" }}>
                    <Login></Login>
                </div>

            </div>

        );
    }
}

export default Menu;