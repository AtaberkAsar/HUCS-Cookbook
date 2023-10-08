import { useState, useEffect } from 'react';

const Threats = () => {
  const [threatData, setThreatData] = useState({"data": []});

  const fetchThreats = async () => {
    try {
      const response = await fetch('http://localhost:8080/threats');
      if (response.ok) {
        const data = await response.json();
        console.log(data)
        setThreatData(data);
      } else {
        console.error('Failed to fetch threats:', response.status, response.statusText);
      }
    } catch (error) {
      console.error('Fetch threats error:', error);
    }
  };

  useEffect(() => {
    fetchThreats();
  }, []);

  return (
    <div>
      <button onClick={fetchThreats}>Fetch Threats</button>
      <table>
        <thead>
          <tr>
            <th>Type</th>
            <th>Threat</th>
            <th>Submitted By</th>
            <th>Timestamp</th>
          </tr>
        </thead>
        <tbody>
          {threatData.data?.map((threat) => (
            <tr key={threat[0]}>
              <td>{threat[1]}</td>
              <td>{threat[2]}</td>
              <td>{threat[3]}</td>
              <td>{threat[4]}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default Threats;
