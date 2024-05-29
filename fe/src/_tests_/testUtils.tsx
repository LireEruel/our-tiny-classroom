// test-utils.tsx
import React from "react";
import { render, RenderOptions } from "@testing-library/react";
import { QueryClient, QueryClientProvider } from "@tanstack/react-query";

// 테스트용 QueryClient를 생성하는 함수
const createTestQueryClient = () =>
  new QueryClient({
    defaultOptions: {
      queries: {
        retry: false, // 테스트 중에는 재시도하지 않도록 설정
      },
    },
  });

// 모든 테스트에서 사용할 수 있는 custom render 함수
const customRender = (
  ui: React.ReactElement,
  options?: Omit<RenderOptions, "queries">
) => {
  const queryClient = createTestQueryClient();
  // eslint-disable-next-line @typescript-eslint/no-explicit-any
  const AllTheProviders: React.FC = ({ children }: any) => (
    <QueryClientProvider client={queryClient}>{children}</QueryClientProvider>
  );

  return render(ui, { wrapper: AllTheProviders, ...options });
};

// eslint-disable-next-line react-refresh/only-export-components
export * from "@testing-library/react";

// custom render를 기본 render로 override
export { customRender as render };
