const Home = () => {
  const goRoom = () => {
    window.location.assign(`/#/join-classroom`);
  };
  return (
    <div>
      <button onClick={goRoom}> 방 입장</button>
    </div>
  );
};

export default Home;
