----------------------------------------------------------------------------------
-- Company: 
-- Engineer: 
-- 
-- Create Date:    19:33:28 11/27/2019 
-- Design Name: 
-- Module Name:    timer - Behavioral 
-- Project Name: 
-- Target Devices: 
-- Tool versions: 
-- Description: 
--
-- Dependencies: 
--
-- Revision: 
-- Revision 0.01 - File Created
-- Additional Comments: 
--
----------------------------------------------------------------------------------
library IEEE;
use IEEE.STD_LOGIC_1164.ALL;

-- Uncomment the following library declaration if using
-- arithmetic functions with Signed or Unsigned values
use IEEE.NUMERIC_STD.ALL;
use IEEE.STD_LOGIC_UNSIGNED.ALL;
-- Uncomment the following library declaration if instantiating
-- any Xilinx primitives in this code.
--library UNISIM;
--use UNISIM.VComponents.all;

entity timer is
    Port ( S : IN  STD_LOGIC;
	 B : IN STD_LOGIC;
	 rst : IN STD_LOGIC;
	 DCT : IN STD_LOGIC;
	 SecRight : OUT STD_LOGIC_VECTOR (3 DOWNTO 0);
	 SecLeft : OUT STD_LOGIC_VECTOR (3 DOWNTO 0);
	 MinRight : OUT STD_LOGIC_VECTOR (3 DOWNTO 0);
	 MinLeft : OUT STD_LOGIC_VECTOR (3 DOWNTO 0);
	 HourRight : OUT STD_LOGIC_VECTOR (3 DOWNTO 0);
	 Hourleft : OUT STD_LOGIC_VECTOR (3 DOWNTO 0)
	 );
end timer;

architecture Behavioral of timer is
signal second1: std_logic_vector (3 downto 0):="0000";
signal second2: std_logic_vector (3 downto 0):="0000";
signal minute1: std_logic_vector (3 downto 0):="0000";
signal minute2: std_logic_vector (3 downto 0):="0000";
signal hour1: std_logic_vector (3 downto 0):="0000";
signal hour2: std_logic_vector (3 downto 0):="0000";

begin

process (DCT,S,rst,second1,second2,minute1,minute2,hour1,hour2)
begin
if (S='0') and rising_edge(DCT) then
	if (B='0') then
	second1<=second1+1;
		if (second1="1001") then
			second1<="0000";
			second2<=second2+1;
			if (second2="0101") then
				second2<="0000";
				minute1<=minute1+1;
				if (minute1="1001") then
					minute1<="0000";
					minute2<=minute2+1;
					if (minute2="0101") then
						minute2<="0000";
						hour1<=hour1+1;
						if (hour1="1001") then
							hour1<="0000";
							hour2<=hour2+1;
							if (hour2="0010") and (hour1="0100") then
								hour1<="0000";
								hour2<="0000";
							end if;
						end if;
					end if;
				end if;
			end if;
		end if;
	else
	second1<=second1-1;
		if (second1="0000") then
			second1<="1001";
			second2<=second2-1;
			if (second2="0000") then
				second2<="0101";
				minute1<=minute1-1;
				if (minute1="0000") then
					minute1<="1001";
					minute2<=minute2-1;
					if (minute2="0000") then
						minute2<="0101";
						hour1<=hour1-1;
						if (hour1="0000") then
							hour1<="1001";
							hour2<=hour2-1;
							if (hour2="0010") and (hour1="0100") then
								hour1<="0000";
								hour2<="0000";
							end if;
						end if;
					end if;
				end if;
			end if;
		end if;
	end if;
	--if rising_edge(rst) then
		--second1<="0000";
		--second2<="0000";
		--minute1<="0000";
		--minute2<="0000";
		--hour1<="0000";	
		--hour2<="0000";
	--end if;
end if;
end process;


SecRight<=second1;
SecLeft<=second2;
MinRight<=minute1;
MinLeft<=minute2;
HourRight<=hour1;
HourLeft<=hour2;

end Behavioral;

