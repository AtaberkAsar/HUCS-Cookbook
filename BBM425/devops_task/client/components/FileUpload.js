import { useState } from 'react';

const FileUpload = () => {
  const [selectedFile, setSelectedFile] = useState(null);

  const handleFileChange = (event) => {
    setSelectedFile(event.target.files[0]);
  };

  const handleUpload = async () => {
    const formData = new FormData();
    formData.append('file', selectedFile);

    try {
      const response = await fetch('http://localhost:8080/uploadFile', {
        method: 'POST',
        body: formData,
      });
      console.log('File upload response:', response);
    } catch (error) {
      console.error('File upload error:', error);
    }
  };

  return (
    <div>
      <div>
        <input type="file" onChange={handleFileChange} />
      </div>
      <div>
        <button onClick={handleUpload}>Upload File</button>
      </div>
    </div>
  );
};

export default FileUpload;
