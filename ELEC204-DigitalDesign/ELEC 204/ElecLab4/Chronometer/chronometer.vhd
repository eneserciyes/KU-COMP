----------------------------------------------------------------------------------
-- Company: 
-- Engineer: 
-- 
-- Create Date:    19:32:44 11/27/2019 
-- Design Name: 
-- Module Name:    chronometer - Behavioral 
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

entity chronometer is
    Port ( S : IN  STD_LOGIC;
	 B : IN STD_LOGIC;
	 rst : IN STD_LOGIC;
	 stp: IN STD_LOGIC;
	 DCC : IN STD_LOGIC;
	 MiliSecRight : OUT STD_LOGIC_VECTOR (3 DOWNTO 0);
	 MiliSecLeft : OUT STD_LOGIC_VECTOR (3 DOWNTO 0);
	 SecRight : OUT STD_LOGIC_VECTOR (3 DOWNTO 0);
	 Secleft : OUT STD_LOGIC_VECTOR (3 DOWNTO 0);
	 MinRight : OUT STD_LOGIC_VECTOR (3 DOWNTO 0);
	 MinLeft : OUT STD_LOGIC_VECTOR (3 DOWNTO 0)
	 );
end chronometer;

architecture Behavioral of chronometer is
signal milisecond1: std_logic_vector (3 downto 0):="0000";
signal milisecond2: std_logic_vector (3 downto 0):="0000";
signal second1: std_logic_vector (3 downto 0):="0000";
signal second2: std_logic_vector (3 downto 0):="0000";
signal minute1: std_logic_vector (3 downto 0):="0000";
signal minute2: std_logic_vector (3 downto 0):="0000";
signal run: std_logic:='1';
begin

process (DCC,S,stp,run,rst,milisecond1,milisecond2,second1,second2,minute1,minute2)
begin
if rising_edge(stp) then
	if (run='1') then
		run<='0';
	else
		run<='1';
		end if;
end if;
if (S='1') and rising_edge(DCC) and (run='1') then
	if (rst='1') then
		milisecond1<="0000";
		milisecond2<="0000";
		second1<="0000";
		second2<="0000";
		minute1<="0000";
		minute2<="0000";
	
	elsif (B='0') then
	milisecond1<=milisecond1+1;
		if (milisecond1="1001") then
			milisecond1<="0000";
			milisecond2<=milisecond2+1;
			if (milisecond2="1001") then
				milisecond2<="0000";
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
								milisecond1<="0000";
								milisecond2<="0000";
								second1<="0000";
								second2<="0000";
								minute1<="0000";
								minute2<="0000";
							end if;
						end if;
					end if;
				end if;
			end if;
		end if;
	else
	milisecond1<=milisecond1-1;
		if (milisecond1="0000") then
			milisecond1<="1001";
			milisecond2<=milisecond2-1;
			if (milisecond2="0000") then
				milisecond2<="1001";
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
								milisecond1<="1001";
								milisecond2<="1001";
								second1<="1001";
								second2<="0101";
								minute1<="1001";
								minute2<="0101";
							end if;
						end if;
					end if;
				end if;
			end if;
		end if;
	end if;
end if;
end process;




MiliSecRight<=milisecond1;
MiliSecLeft<=milisecond2;
SecRight<=second1;
SecLeft<=second2;
MinRight<=minute1;
MinLeft<=minute2;

end Behavioral;

