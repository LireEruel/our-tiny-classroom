const SVGComponent = (props: object) => (
  <svg
    xmlns="http://www.w3.org/2000/svg"
    xmlnsXlink="http://www.w3.org/1999/xlink"
    viewBox="0 0 40 40"
    width={40}
    height={40}
    fill="none"
    {...props}
  >
    <svg
      xmlns="http://www.w3.org/2000/svg"
      height={40}
      width={40}
      viewBox="0 0 24 24"
      fill="#030303"
      x={0}
      y={0}
      opacity="100%"
      {...props}
    >
      <path d="M0 0h24v24H0z" fill="none" />
      <path d="M3 17.25V21h3.75L17.81 9.94l-3.75-3.75L3 17.25zM20.71 7.04a.996.996 0 0 0 0-1.41l-2.34-2.34a.996.996 0 0 0-1.41 0l-1.83 1.83 3.75 3.75 1.83-1.83z" />
    </svg>
    <defs>
      <filter
        id="filter_dshadow_0_0_0_00000014"
        colorInterpolationFilters="sRGB"
        filterUnits="userSpaceOnUse"
      >
        <feFlood floodOpacity={0} result="bg-fix" />
        <feColorMatrix
          in="SourceAlpha"
          type="matrix"
          values="0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 127 0"
          result="alpha"
        />
        <feOffset dx={0} dy={0} />
        <feGaussianBlur stdDeviation={0} />
        <feComposite in2="alpha" operator="out" />
        <feColorMatrix
          type="matrix"
          values="0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0.08 0"
        />
        <feBlend
          mode="normal"
          in2="bg-fix"
          result="bg-fix-filter_dshadow_0_0_0_00000014"
        />
        <feBlend
          in="SourceGraphic"
          in2="bg-fix-filter_dshadow_0_0_0_00000014"
          result="shape"
        />
      </filter>
    </defs>
  </svg>
);
export default SVGComponent;
