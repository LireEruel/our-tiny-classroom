import ScheduleBoard from "../components/ScheduleBoard";

function EditSchedules() {
  return (
    <div className=" h-full w-[90%] mx-auto">
      <h1 className="text-2xl pl-5 pt-5 font-bold">일정 관리</h1>
      <div className=" h-[80%] ">
        <ScheduleBoard editMode={true} />
      </div>
    </div>
  );
}

export default EditSchedules;
