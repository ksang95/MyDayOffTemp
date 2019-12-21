import React, { Component } from 'react';

class SelectPreview extends Component {
    static defaultProps = {
        selects: []
    }



    render() {
        const { selects, deleteName, name, onClick } = this.props;
        const selectsPreview = selects.map((s, index) => ( <span key={index} name={deleteName} value={index} onClick={onClick}>{s[name]} <span style={{color:'#e03131'}}>&times;</span> &nbsp;&nbsp;</span> ));
        return (
            <div>{selectsPreview}</div>
        );
    }
}

export default SelectPreview;