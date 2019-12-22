import React, { Component } from 'react';

class PayInfo extends Component {
    static defaultProps = {
        info: []
    }
    render() {
        if (this.props.info) {
            const sum = this.props.totalPrice;
            const { totalPay, orderDate, gradeDiscount, couponDiscount, pointUse, totalRefund } = this.props.info;
            return (
                <div className="payInfo">
                    <table>
                        <tbody>

                    <tr><td>주문날짜</td><td>{orderDate}</td></tr>
                    <tr><td>주문금액</td><td>{sum}원</td></tr>
                    <tr><td>등급할인</td><td>-{gradeDiscount?gradeDiscount:0}원</td></tr>
                    <tr><td>쿠폰할인</td><td>-{couponDiscount?couponDiscount:0}원</td></tr>
                    <tr><td>적립금사용</td><td>-{pointUse?pointUse:0}원</td></tr>
            {totalPay?<tr><td>최종 결제금액</td><td>{totalPay}원</td></tr>:<tr><td>환불받을 금액</td><td>{totalRefund}원</td></tr>}
                        </tbody>
                    </table>
                </div>
            );
        }
        else return (<div></div>);
    }
}

export default PayInfo;