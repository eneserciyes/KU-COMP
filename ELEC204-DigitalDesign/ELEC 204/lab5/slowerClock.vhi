
-- VHDL Instantiation Created from source file slowerClock.vhd -- 10:15:19 12/13/2019
--
-- Notes: 
-- 1) This instantiation template has been automatically generated using types
-- std_logic and std_logic_vector for the ports of the instantiated module
-- 2) To use this template to instantiate this entity, cut-and-paste and then edit

	COMPONENT slowerClock
	PORT(
		clk : IN std_logic;          
		SlowClock : OUT std_logic
		);
	END COMPONENT;

	Inst_slowerClock: slowerClock PORT MAP(
		clk => ,
		SlowClock => 
	);


