import React, { Component } from 'react';

class OrderType extends Component {
    static defaultProps = {
        info: null
    }


    render() {
        const { info } = this.props;
        if (info.deliverId) {
            const { invoice, deliverName, deliverLocation, deliverPostalCode, deliverPhone } = info;
            return (
                <div className="orderDeliver">
                    배송 정보
                    <table>
                        <tbody>

                            <tr>
                                <td>수령인</td><td>{deliverName}</td>
                            </tr>
                            <tr>
                                <td>배송지</td><td>{deliverLocation}</td>
                            </tr>
                            <tr>
                                <td>우편번호</td><td>{deliverPostalCode}</td>
                            </tr>
                            <tr>
                                <td>연락처</td><td>{deliverPhone}</td>
                            </tr>
                            <tr>
                                <td>송장번호</td><td>CJ대한통운 {invoice} <a href={"https://tracker.delivery/#/kr.cjlogistics/" + invoice} target="_blank">배송조회</a></td>
                            </tr>
                        </tbody>
                    </table>
                </div>
            );
        }
        else {
            const { storesName, storesLocation } = info;
            return (
                <div className="orderPickUp">
                    픽업 정보
                    <table>
                        <tbody>

                            <tr>
                                <td>픽업 매장</td><td>{storesName}</td>
                            </tr>
                            <tr>
                                <td>주소</td><td>{storesLocation}</td>
                            </tr>
                        </tbody>
                    </table>
                </div>
            );
        }
    }


}

export default OrderType;