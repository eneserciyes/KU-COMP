----------------------------------------------------------------------------------
-- Company: 
-- Engineer: 
-- 
-- Create Date:    19:31:49 11/27/2019 
-- Design Name: 
-- Module Name:    clock_divider - Behavioral 
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
--use IEEE.NUMERIC_STD.ALL;

-- Uncomment the following library declaration if instantiating
-- any Xilinx primitives in this code.
--library UNISIM;
--use UNISIM.VComponents.all;

entity clock_divider is
    Port ( clk : IN  STD_LOGIC;
	 DCT: OUT STD_LOGIC;
	 DCC: OUT STD_LOGIC
	 );
end clock_divider;

architecture Behavioral of clock_divider is
signal counterT:integer:=0;
signal counterC:integer:=0;
signal dividedClockTimer: std_logic:='0';
signal dividedClockChronometer: std_logic:='0';

begin

timer: process (clk)
begin
if rising_edge(clk) then
counterT<=counterT+1;
dividedClockTimer<='0';
	if(counterT=100000000) then
	dividedClockTimer<='1';
	counterT<=0;
	end if;
counterC<=counterC+1;
dividedClockChronometer<='0';
	if(counterC=1000000) then
	dividedClockChronometer<='1';
	counterC<=0;
	end if;
end if;
end process;

DCT<=dividedClockTimer;
DCC<=dividedClockChronometer;

end Behavioral;
