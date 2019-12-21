import React, { Component } from 'react';
import DropzoneComponent from 'react-dropzone-component';
import 'react-dropzone-component/styles/filepicker.css';

class ProductImageForm extends Component {

    constructor(props) {
        super(props);
        var ReactDOMServer = require('react-dom/server');
        const {maxFile}=this.props;
        // For a full list of possible configurations,
        // please consult http://www.dropzonejs.com/#configuration

        this.djsConfig = {
            acceptedFiles: "image/jpg, image/jpeg, image/png, image/gif",
            autoProcessQueue: false,
            maxFiles:maxFile,
            dictDefaultMessage: `여기로 파일을 드래그하거나 여기를 클릭하세요. ${maxFile?'(개수 제한 : '+maxFile+')':''}`,
            previewTemplate: ReactDOMServer.renderToStaticMarkup(
                <div className="dz-preview dz-file-preview" style={{display:"inline-block"}}>
                  <div className="dz-details">
                    <img data-dz-thumbnail="true" />
                    <span className="dz-remove" data-dz-remove>&times;</span>
                  </div>
                  <div className="dz-progress"><span className="dz-upload" data-dz-uploadprogress="true"></span></div>
                </div>
              )
        };
        
        this.componentConfig = {
            iconFiletypes: ['.jpg', '.jpeg', '.png', '.gif'],
            showFiletypeIcon: true,
            postUrl: 'none'
        };

        this.dropzone = null;
    }

    shouldComponentUpdate(nextProps, prevState) {
        if(this.props.post===false && nextProps.post===true){
            this.dropzone.removeAllFiles();
            return true;
        }
        return false;
    }

    onAdd=(file)=>{
        this.props.onAdd(file,this.props.stateKey);
    }

    onRemove=(file)=>{
        this.props.onRemove(file,this.props.stateKey);
    }

    render() {
        const {name}=this.props;
        const config = this.componentConfig;
        const djsConfig = this.djsConfig;
        // For a list of all possible events (there are many), see README.md!
        const eventHandlers = {
            init: (dz) => {this.dropzone = dz; this.dropzone.on("maxfilesexceeded", function(file) {
                this.removeAllFiles();
                this.addFile(file);
          });},
            addedfile: this.onAdd,
            removedfile: this.onRemove
        }

        return (
            <div>
                <span>{name}</span>
                <DropzoneComponent config={config} eventHandlers={eventHandlers} djsConfig={djsConfig} />
            </div>
        );
    }


}

export default ProductImageForm;