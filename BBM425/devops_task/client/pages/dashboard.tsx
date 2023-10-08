import Head from 'next/head';
import FileUpload from '../components/FileUpload';
import Threats from '../components/Threats';

const DashboardPage = () => {
  return (
    <div>
      <Head>
        <title>DevOps Task</title>
      </Head>

      <main>
        <h1>Welcome GUEST</h1>
        <FileUpload />
        <Threats />
      </main>
    </div>
  );
}

export default DashboardPage;
